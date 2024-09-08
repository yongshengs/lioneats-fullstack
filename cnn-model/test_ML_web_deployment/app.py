from flask import Flask,request,jsonify,render_template
import tensorflow as tf
import os
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
import numpy as np

model = load_model('D:/ML/ADproject/best_model.keras')  
app = Flask(__name__)

# 设置文件上传的目标目录
UPLOAD_FOLDER = 'D:/ML/ADproject/test_ML_web_deployment/uploads_to_predict'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
Train_dir = 'D:/ML/ADproject/train'

# 创建上传目录（如果不存在）
if not os.path.exists(UPLOAD_FOLDER):
    os.makedirs(UPLOAD_FOLDER)
# 类别列表
# class_names = ["Cendol","Chai_Tow_Kway","Chicken_Rice","Chilli_Crab","Curry_Puff"
#              ,"Fish_Head_Curry","Nasi_Lemak","Roti_Prata","Satay","Teo_Kueh"]
class_names = [name for name in os.listdir(Train_dir) if os.path.isdir(os.path.join(Train_dir, name))]

def prepare_image(img_path):
    img = image.load_img(img_path, target_size=(224, 224))
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    x = x / 255.  # 标准化
    return x
@app.route('/')
def uploads():
    return render_template('uploads.html')

@app.route('/predict', methods=['POST'])
def predict():
    print(request.files)
    if 'file' not in request.files:
        return jsonify({'error': 'No file part'})

    file = request.files['file']

    if file.filename == '':
        return jsonify({'error': 'No selected file'})

    if file:
        img_path = os.path.join(app.config['UPLOAD_FOLDER'], file.filename)
        file.save(img_path)
        img = prepare_image(img_path)

        predictions = model.predict(img)
        predicted_class = np.argmax(predictions, axis=1)[0]
        predicted_class_name = class_names[predicted_class]

        return jsonify({'predicted_class': predicted_class_name})

if __name__ == '__main__':
    app.run(debug=True)