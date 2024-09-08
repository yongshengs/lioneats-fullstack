import numpy as np
import cv2
import tensorflow as tf
import matplotlib.pyplot as plt
import os

IMG_HEIGHT = 128
IMG_WIDTH = 128
# 加载模型
model = tf.keras.models.load_model('best_model.keras')

# 类别标签
Train_dir = 'D:/ML/ADproject/train'
class_names = [name for name in os.listdir(Train_dir) if os.path.isdir(os.path.join(Train_dir, name))]

# 图像预处理函数
def preprocess_image(image, target_size):
    image_resized = cv2.resize(image, target_size)
    image_rgb = cv2.cvtColor(image_resized, cv2.COLOR_BGR2RGB)
    image_normalized = image_rgb / 255.0
    return np.expand_dims(image_normalized, axis=0)

# 加载图像并进行预测
def predict_and_print(model, image_path):
    image = cv2.imread(image_path)
    processed_image = preprocess_image(image, (IMG_WIDTH, IMG_HEIGHT))
    
    predictions = model.predict(processed_image)
    predicted_class = np.argmax(predictions)
    predicted_prob = predictions[0][predicted_class]
    print(f"Predicted class: {class_names[predicted_class]} with probability {predicted_prob:.2f}")
    for idx, prob in enumerate(predictions[0]):
        print(f"{class_names[idx]}: {prob:.2f}")

    
# 主函数
def main():
    image_path = 'D:/ML/ADproject/test/Cendol/14.jpg'  # 测试图像路径
    predict_and_print(model,image_path=image_path)
    

if __name__ == '__main__':
    main()