# %%
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt
import math

# %%
from tensorflow.keras import layers
from tensorflow.keras.layers import Conv2D,MaxPooling2D,Flatten,Dropout,Dense
from tensorflow.keras.models import Sequential
from tensorflow.keras.preprocessing.image import ImageDataGenerator

# %% [markdown]
# ### Create a CNN model

# %%
def create_model():
    model = Sequential()

    # create a CNN model
    model.add(Conv2D(filters=64,kernel_size=(3, 3), activation='relu', input_shape=(128,128,3)))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Dropout(0.2))
    model.add(Conv2D(filters=128,kernel_size=(3, 3), activation='relu'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Dropout(0.2))
    model.add(Conv2D(filters=256,kernel_size=(3, 3), activation='relu'))
    model.add(MaxPooling2D(pool_size=(2, 2)))
    model.add(Dropout(0.2))
    model.add(Flatten())
    model.add(Dense(units=512, activation='relu')) 
    model.add(Dropout(0.2))
    # create output layer 
    model.add(Dense(units=4 ,activation= 'softmax'))
    
    # bulid the model
    # optimizer = tf.keras.optimizers.Adam(learning_rate=0.001)
    model.compile(optimizer='adam',loss='categorical_crossentropy',metrics=['accuracy'])

    return model

# %% [markdown]
# ### evalue performance

# %%
from sklearn.metrics import confusion_matrix, precision_score, recall_score, f1_score
def evaluate_model_performance(model, test_generator):
    Y_pred = model.predict(test_generator)
    y_pred = np.argmax(Y_pred, axis=1)
    y_true = test_generator.classes

    cm = confusion_matrix(y_true, y_pred)
    precision = precision_score(y_true, y_pred, average=None)
    recall = recall_score(y_true, y_pred, average=None)
    f1 = f1_score(y_true, y_pred, average=None)

    print("Confusion Matrix:")
    print(cm)
    print(f"Precision: {precision}")
    print(f"Recall: {recall}")
    print(f"F1 Score: {f1}")


# %% [markdown]
# ### set seed 

# %%
seed_value = 42
tf.random.set_seed(seed_value)
np.random.seed(seed_value)

# %% [markdown]
# ### load data 

# %%
# image augmentation --to increase training dataset diversity , improve accuracy
train_gen = ImageDataGenerator(
    rescale=1./255,
    shear_range =0.2,
    zoom_range = 0.2,
    horizontal_flip = True,
    width_shift_range = 0.2,
    height_shift_range = 0.2,
    fill_mode='nearest',
    rotation_range = 20,
    validation_split = 0.2# set up validation set take 20% of train file
    )
# leverage on  generator -- to deal with more data 
train_generator = train_gen.flow_from_directory (
    directory = 'D:/ML/CNN/CA/train',
    target_size = (128,128),
    color_mode = 'rgb',
    batch_size = 32,
    shuffle = True,
    class_mode = 'categorical',#categorical would auto-generate one-hot code
    subset = 'training',# subset是和validation_split 一起使用的 
    seed = seed_value  
)
valid_gen = ImageDataGenerator(
rescale=1./255,
validation_split = 0.2)
validation_generator = valid_gen.flow_from_directory (
    directory = 'D:/ML/CNN/CA/train',
    target_size = (128,128),
    color_mode = 'rgb',
    shuffle = False,
    batch_size = 32,
    class_mode = 'categorical',
    subset = 'validation',
    seed = seed_value
)
test_gen = ImageDataGenerator(rescale=1./255)
test_generator = test_gen.flow_from_directory (
    directory = 'D:/ML/CNN/CA/test',
    target_size = (128,128),
    color_mode = 'rgb',
    shuffle = False,
    batch_size = 32,
    class_mode = 'categorical'
    #subset = 'testing'####因此这里不需要subset
)

# %% [markdown]
# ### create model

# %%
from tensorflow.keras.callbacks import EarlyStopping
model = create_model()
early_stopping = EarlyStopping(monitor='val_loss' , patience = 5 , mode = 'min')
reduce_lr = tf.keras.callbacks.ReduceLROnPlateau(monitor='val_loss', factor=0.2, patience=5, min_lr=0.0001)

# %%
#class weight (0,1,2,3 denotes apple ,banana,mixed,orange)
class_weights = {
    0: 1.0,
    1: 1.0,
    2: 2.5,
    3: 1.0
}

# %% [markdown]
# ### train the model

# %%
#train the model
history = model.fit(
    x=train_generator,
    validation_data = validation_generator,
    epochs=50,
    # steps_per_epoch=train_generator.samples//train_generator.batch_size, # in general ，it can be calculate automatically，even not exact division
    # validation_steps=validation_generator.samples//validation_generator.batch_size, ### here because 20% trainset cant be exact divided by batchsize（32）， so it failed
    callbacks = [early_stopping,reduce_lr]
    )

loss, acc = model.evaluate(test_generator)

# %%
print("Loss = " , loss ,", accuracy = ",acc)
final_train_accuracy = history.history['accuracy'][-1]
final_val_accuracy = history.history['val_accuracy'][-1]
print(f"Training Accuracy: {final_train_accuracy}")
print(f"Validation Accuracy: {final_val_accuracy}")

# %%
evaluate_model_performance(model,test_generator)

# %% [markdown]
# ### plot 

# %%
accuracy = history.history['accuracy']
val_accuracy = history.history['val_accuracy']
loss = history.history['loss']
val_loss = history.history['val_loss']

# %%
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

# %% [markdown]
# ### test

# %%
import tensorflow as tf
from tensorflow.keras.preprocessing import image
from tensorflow.keras.applications.resnet50 import preprocess_input, decode_predictions
import numpy as np

# 类别列表
class_names = ['apple', 'banana', 'mixed', 'orange']
# 图像预处理
img_path = 'D:/ML/CNN/CA/test/mixed/mixed_21.jpg'
img = image.load_img(img_path, target_size=(128, 128))  # 调整大小为模型的输入大小
x = image.img_to_array(img)
x = np.expand_dims(x, axis=0)
x = x / 255.  # 标准化，与数据生成器中的 rescale 参数一致

# 进行预测
predictions = model.predict(x)
predicted_class = np.argmax(predictions, axis=1)[0]
predicted_class = class_names[predicted_class]

# 输出预测结果
print('Predicted:', predicted_class)

model.save('cnn_model.keras')

