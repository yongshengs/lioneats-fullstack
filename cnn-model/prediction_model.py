#Import Everything 
from altair import layer
import numpy as np
import pandas as pd
import os
import math 
import seaborn as sns
import matplotlib.pyplot as plt
import tensorflow as tf 
from tensorflow.keras.applications import ResNet50,VGG16, EfficientNetB0,InceptionV3
from tensorflow.keras import layers
from tensorflow.keras.layers import Conv2D,MaxPooling2D,AveragePooling2D,Flatten,Dropout,Dense,Concatenate,GlobalAveragePooling2D
from tensorflow.keras.regularizers import l1, l2
from tensorflow.keras.models import Sequential
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.optimizers import Adam
import cv2
#define global variable
Train_dir = 'D:/ML/ADproject/train'
Validation_dir = 'D:/ML/ADproject/train'
Test_dir = 'D:/ML/ADproject/test'
TF_MODEL = 'cnn_model.tflite'
MyModel_NAME = 'cnn_model.keras' # web deployment
Saved_dir = 'cnn_model.keras'  # mobile deployment
Best_Model_dir = 'best_model.keras'

CLASS_NUM = 10 # how many class should we classify(can not be changed)
IMG_HEIGHT = 128
IMG_WIDTH = 128
BATCH_SIZE = 32 
VALIDATION_SPLIT = 0.2
EPOCHS = 100
# regularization and Earlystopping hyperparameter
PATIENCE = 5
FACTOR = 0.1 #learning rate reduce ratio
MIN_LEARNING_RATE = 0.000001


# define mixed pooling layer 
# class MixPooling2D(tf.keras.layers.Layer):
#     def __init__(self, pool_size=(2, 2),**kwargs):
#         super(MixPooling2D, self).__init__(**kwargs)
#         self.max_pooling = MaxPooling2D(pool_size)
#         self.avg_pooling = AveragePooling2D(pool_size)
#         self.concat = Concatenate()

#     def call(self, inputs):
#         max_pooled = self.max_pooling(inputs)
#         avg_pooled = self.avg_pooling(inputs)
#         return self.concat([max_pooled, avg_pooled])
# def mix_pooling():
#     max_pooling = MaxPooling2D(pool_size =(2,2))
#     avg_pooling = AveragePooling2D(pool_size=(2,2))
#     return Concatenate()([max_pooling,avg_pooling])
# create the cnn model
def create_model():
   
    # #use pre-trained model to extract feature
    # base_model = VGG16(weights='imagenet', include_top=False, input_shape=(IMG_WIDTH, IMG_HEIGHT, 3))
    
    # # freeze conv layers 
    # for layer in base_model.layers:
    #     layer.trainable = False
    # #fine-tuning unfreeze layers
    # for layer in base_model.layers[-30:]:
    #     layer.trainable = True
    #create CNN model 
    model = Sequential()# Stacks layers in a linear order, simplifying model construction.
    model.add(Conv2D(filters=32,kernel_size=(3, 3), activation='relu', input_shape=(IMG_WIDTH,IMG_HEIGHT,3)))
    model.add(MaxPooling2D((2,2)))
    model.add(Dropout(rate=0.2))
    model.add(Conv2D(filters=64,kernel_size=(3, 3), activation='relu'))
    model.add(MaxPooling2D((2,2)))
    model.add(Dropout(rate=0.2))
    model.add(Conv2D(filters=128,kernel_size=(3, 3), activation='relu'))
    model.add(MaxPooling2D((2,2)))
    model.add(Dropout(rate=0.2))
    model.add(Conv2D(filters=256,kernel_size=(3, 3), activation='relu'))
    model.add(MaxPooling2D((2,2)))
    model.add(Conv2D(filters=512,kernel_size=(3, 3), activation='relu'))
    model.add(MaxPooling2D((2,2)))
    model.add(Dropout(0.2))
    model.add(Conv2D(filters=512,kernel_size=(3, 3), activation='relu',padding="SAME"))
    model.add(MaxPooling2D((2,2)))
    model.add(Dropout(rate=0.2))
 

    model.add(GlobalAveragePooling2D())

    model.add(Dense(units=512,activation='relu'))
    model.add(Dropout(0.2))
    model.add(Dense(units=256,activation='relu'))
    #create output layer
    model.add(Dense(units=CLASS_NUM,activation='softmax'))
    model.compile(optimizer='adam',loss='categorical_crossentropy',metrics=['accuracy'])
    
    return model 


#evaluation the model performance 
from sklearn.metrics import confusion_matrix, precision_score, recall_score, f1_score
def evaluate_model_performance(model, test_generator):
    Y_pred = model.predict(test_generator)
    y_pred = np.argmax(Y_pred, axis=1)
    y_true = test_generator.classes
    # labels =["Cendol","Chai_Tow_Kway","Chicken_Rice","Chilli_Crab","Curry_Puff"
    #          ,"Fish_Head_Curry","Nasi_Lemak","Roti_Prata","Satay","Teo_Kueh"]
    cm = confusion_matrix(y_true, y_pred)
    precision = precision_score(y_true, y_pred, average=None)
    recall = recall_score(y_true, y_pred, average=None)
    f1 = f1_score(y_true, y_pred, average=None)
    # 自动获取文件夹名称作为标签
    labels = [name for name in os.listdir(Train_dir) if os.path.isdir(os.path.join(Train_dir, name))]
    cm_df = pd.DataFrame(cm,index= labels,columns= labels)
    print("Confusion Matrix:")
    # Plot the heatmap
    print(cm_df)
    print(f"Precision: {precision}")
    print(f"Recall: {recall}")
    print(f"F1 Score: {f1}")

#using OpenCV to do data preprocessing 
def random_rotation(image):
    angle = np.random.uniform(-20, 20)
    h, w = image.shape[:2]
    M = cv2.getRotationMatrix2D((w/2, h/2), angle, 1)
    return cv2.warpAffine(image, M, (w, h))

def random_translation(image):
    x_shift = np.random.uniform(-0.2, 0.2) * image.shape[1]
    y_shift = np.random.uniform(-0.2, 0.2) * image.shape[0]
    M = np.float32([[1, 0, x_shift], [0, 1, y_shift]])
    return cv2.warpAffine(image, M, (image.shape[1], image.shape[0]))

def random_scaling(image):
    scale = np.random.uniform(0.8, 1.2)
    return cv2.resize(image, None, fx=scale, fy=scale)

def random_flip(image):
    flip_code = np.random.choice([-1, 0, 1])  # -1: both axes, 0: vertical, 1: horizontal
    return cv2.flip(image, flip_code)

def random_brightness(image):
    value = np.random.uniform(0.8, 1.2)
    hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    hsv = np.array(hsv, dtype=np.float64)
    hsv[:,:,2] = hsv[:,:,2] * value
    hsv[:,:,2][hsv[:,:,2] > 255] = 255
    hsv = np.array(hsv, dtype=np.uint8)
    return cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)

def random_contrast(image):
    value = np.random.uniform(0.8, 1.2)
    image = np.array(image, dtype=np.float64)
    image = image * value
    image[image > 255] = 255
    return np.array(image, dtype=np.uint8)

def augment_image(image):
    image = random_rotation(image)
    image = random_translation(image)
    image = random_scaling(image)
    image = random_flip(image)
    image = random_brightness(image)
    image = random_contrast(image)
    return image

def preprocess_image(image, target_size):
    image_resized = cv2.resize(image, target_size)
    image_normalized = image_resized / 255.0
    return image_normalized

def custom_image_generator(generator, target_size):
    for batch in generator:
        batch_images = batch[0]
        batch_labels = batch[1]
        processed_images = np.array([preprocess_image(augment_image(img), target_size) for img in batch_images])
        yield processed_images, batch_labels
# get data via keras ImageGenerator
def generate_data():
    # for experiment wise , in real industry can delete
    seed_value =  42
    tf.random.set_seed(seed_value)
    np.random.seed(seed_value)
    # image augmentation --to increase training dataset diversity , improve accuracy
    train_gen = ImageDataGenerator(
        rescale=1./255,
        # shear_range =0.2,
        # zoom_range = 0.2,
        # horizontal_flip = True,
        # width_shift_range = 0.2,
        # height_shift_range = 0.2,
        # fill_mode='nearest',
        # rotation_range = 20 ,
        # brightness_range=[0.8, 1.2],  # 添加亮度调整
        validation_split = VALIDATION_SPLIT # set up validation set take 30% of train file
        )
    # leverage on  generator -- to deal with more data 
    train_generator = train_gen.flow_from_directory (
        directory = Train_dir,
        target_size = (IMG_WIDTH,IMG_HEIGHT),
        color_mode = 'rgb',
        batch_size = BATCH_SIZE,
        shuffle = True,#default  is True
        class_mode = 'categorical',#categorical would auto-generate one-hot code
        subset = 'training',# subset是和validation_split 一起使用的 
        seed = seed_value  
    )
    valid_gen = ImageDataGenerator(
    rescale=1./255,
    validation_split = VALIDATION_SPLIT)
    validation_generator = valid_gen.flow_from_directory (
        directory = Validation_dir,
        target_size = (IMG_WIDTH,IMG_HEIGHT),
        color_mode = 'rgb',
        shuffle = False,
        batch_size = BATCH_SIZE,
        class_mode = 'categorical',
        subset = 'validation',
        seed = seed_value
    )
    test_gen = ImageDataGenerator(rescale=1./255)
    test_generator = test_gen.flow_from_directory (
        directory = Test_dir,
        target_size = (IMG_WIDTH,IMG_HEIGHT),
        color_mode = 'rgb',
        shuffle = False,
        batch_size = BATCH_SIZE,
        class_mode = 'categorical'
    )
    return custom_image_generator(train_generator, (IMG_WIDTH, IMG_HEIGHT)),validation_generator,test_generator

# train the model 
from tensorflow.keras.callbacks import EarlyStopping,ModelCheckpoint
from sklearn.utils.class_weight import compute_class_weight
def train_model(model,train_set,validation_set):

    #using early stop and regularization to avoid overfitting
    early_stopping = EarlyStopping(monitor='val_loss' , patience = 7 , mode = 'min')
    reduce_lr = tf.keras.callbacks.ReduceLROnPlateau(monitor='val_loss', factor=FACTOR , patience=PATIENCE , min_lr=MIN_LEARNING_RATE)
    checkpoint = ModelCheckpoint(filepath=Best_Model_dir, monitor='val_loss', save_best_only=True)
    # 提取标签
    # train_labels = train_set.classes
    # class_weight = compute_class_weight('balanced', classes=np.unique(train_labels), y=train_labels)
    # class_weight_dict = dict(enumerate(class_weight))

    history = model.fit(
    x=train_set,
    validation_data = validation_set,
    epochs=EPOCHS,
    # class_weight=class_weight_dict,
    callbacks = [reduce_lr,checkpoint,early_stopping]
    )

    return history

# print eavluation loss and accuray 
def print_loss_and_acc(history):
    final_train_loss = history.history['loss'][-1]
    fianl_val_loss = history.history['val_loss'][-1]
    final_train_accuracy = history.history['accuracy'][-1]
    final_val_accuracy = history.history['val_accuracy'][-1]
    print(f"Training Accuracy: {final_train_accuracy}")
    print(f"Validation Accuracy: {final_val_accuracy}")
    print(f"Training Loss: {final_train_loss}")
    print(f"Validation Loss: {fianl_val_loss}")


# plot function 
def plot_prediction(history):
    accuracy = history.history['accuracy']
    val_accuracy = history.history['val_accuracy']
    loss = history.history['loss']
    val_loss = history.history['val_loss']
    #plot accuracy 
    plt.subplot(1,2,1)
    plt.plot(accuracy,label = 'Training Accuracy')
    plt.plot(val_accuracy,label = 'Validation Accuracy')
    plt.title('Train and Validation Accuracy')
    plt.xlabel('Epochs')
    plt.ylabel('Accuracy')
    plt.legend()

    #plot loss
    plt.subplot(1,2,2)
    plt.plot(loss,label = 'Training Loss')
    plt.plot(val_loss,label = 'Validation Loss')
    plt.title('Train and Validation Loss')
    plt.xlabel('Epochs')
    plt.ylabel('Loss')
    plt.legend()

    #adjust internal of subplot
    plt.tight_layout()
    plt.show()

def preprocess_image(image, target_size):
    image_resized = cv2.resize(image, target_size)
    image_rgb = cv2.cvtColor(image_resized, cv2.COLOR_BGR2RGB)
    image_normalized = image_rgb / 255.0
    return image_normalized

def custom_image_generator(generator, target_size):
    for batch in generator:
        #get image and respective label
        batch_images = batch[0]
        batch_labels = batch[1]
        #processing every images
        processed_images = np.array([preprocess_image(img, target_size) for img in batch_images])
        #pause generator function, when called again, it continue to processing images
        yield processed_images, batch_labels


def save_model(model,saved_name):
    model.save(saved_name)
    return save_model

def convert_model_to_tflite(saved_dir):
    # loading the trained model
    model = tf.keras.models.load_model(saved_dir)

    # convert to TensorFlow Lite Format
    converter = tf.lite.TFLiteConverter.from_keras_model(model)
    tflite_model = converter.convert()

    # save as .tflite file
    with open(TF_MODEL, 'wb') as f:
        f.write(tflite_model)


'''
    Main 
'''
def main():
    model = create_model()

    train_set,validation_set,test_set = generate_data()

    history = train_model(model,train_set,validation_set)
    #print train evaluation
    print_loss_and_acc(history)
    plot_prediction(history=history)
    #print test evaluation
    loss,acc = model.evaluate(test_set)
    print("Test Loss = " , loss ,", Test Accuracy = ", acc)
    # evaluate actual performance of the classification
    evaluate_model_performance(model,test_set)
   
   
    #after save best model , no need to train anymore
    # best_model = tf.keras.models.load_model(Best_Model_dir)
    
    # loss,acc = best_model.evaluate(test_set)
    # print("Test Loss = " , loss ,", Test Accuracy = ", acc)
    # evaluate_model_performance(best_model,test_set)
   
   
    # #save model foe web deployment
    # save_model(model,MyModel_NAME)
    # #convert model for mobile deployment
    # convert_model_to_tflite(Saved_dir)

if __name__ == '__main__':
    main()

