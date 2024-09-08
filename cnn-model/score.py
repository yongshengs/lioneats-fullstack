import json
import numpy as np
import requests
from PIL import Image
import tensorflow as tf
import io
import os
# from azureml.core import Dataset , Workspace


# Train_dir = 'D:/ML/ADproject/train'
# class_names = [name for name in os.listdir(Train_dir) if os.path.isdir(os.path.join(Train_dir, name))]
def init():
    global model
    global class_names
    # model = tf.keras.models.load_model('best_model.keras')
    os.environ["AZUREML_MODEL_DIR"] = "D:/ML/ADproject"
    # print("AZUREML_MODEL_DIR:", os.getenv("AZUREML_MODEL_DIR"))
    model_path = os.path.join(os.getenv("AZUREML_MODEL_DIR"), "best_model.keras")
    model = tf.keras.models.load_model(model_path)
    # ws = Workspace.from_config()
    # dataset = Dataset.get_by_name(ws, name='class_names_dataset')
    # class_names = dataset.to_pandas_dataframe().squeeze().tolist()
    class_names = ['Cendol','Chicken_Rice','Chili_Crab','Curry_Puff','Nasi_Lemak',
                   'Pulut_Hitam','Roti_Prata','Satay','Soon_Kueh','Teo_Kueh']

def preprocess_image(image):
    image = image.resize((224, 224))
    image = np.array(image) / 255.0
    image = np.expand_dims(image, axis=0)
    return image

def run(data):
    try:
        data = json.loads(data)
        image_url = data['image_url']
        response = requests.get(image_url)
        image = Image.open(io.BytesIO(response.content))
        processed_image = preprocess_image(image)
        predictions = model.predict(processed_image)
        predicted_class = np.argmax(predictions, axis=1)[0]
        predicted_class_name = class_names[predicted_class]
        return json.dumps({"predicted_class": predicted_class_name})
    except Exception as e:
        return json.dumps({"error": str(e)})

## only  for  local test
test_data = json.dumps({"image_url": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtnuJzJm-rNT9x_KSqLtpkaNTm97U1ABwaTg&usqp=CAU"})
init()
result = run(test_data)
print(result)