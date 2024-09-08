import tensorflow as tf

# 加载已训练的模型
model = tf.keras.models.load_model('D:\ML\CNN\CA\cnn_model.keras')

# 转换为 TensorFlow Lite 格式
converter = tf.lite.TFLiteConverter.from_keras_model(model)
tflite_model = converter.convert()

# 保存为 .tflite 文件
with open('cnn_model.tflite', 'wb') as f:
    f.write(tflite_model)