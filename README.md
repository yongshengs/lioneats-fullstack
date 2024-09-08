Files included:
FE Web (React) - web-frontend
FE Mobile (Android Java) - mobile-frontend
BE (Java Spring Boot) - backend
ML model (Python, Keras, Flask) - cnn-model

# Recommended setup
1. Split each of the 4 components into their own repository and run them separately
2. You will require some kind of SQL workbench software to run the db layer locally if you prefer. We used MySQL Workbench during testing, and Azure for the actual presentation.

## web-frontend
How to install and set up React app in your computer:
1) Load up terminal and cd to the folder you want the frontend app to reside in
2) Ensure that you have downloaded and installed vite
3) Run "npm create vite@latest lioneats-frontend"
4) Follow the instructions on the screen in the CLI
5) Install a few other packages in your app's directory:
  - npm install bootstrap --save
  - npm install axios --save
  - npm install react-router-dom --save

6) Take note to add in "import 'bootstrap/dist/css/bootstrap.min.css'" in the Main.jsx file

## cnn-model
Our code is designed to recognize 10 Singaporean dishes using CNN model.

Here is some steps you may need to do beforehand:
1. config your python version at least above python 3.8
2. download the sklearn, tensorflow, keras, numpy , pillow , pandas
3. pip install azureml-ai to get permission using Python SDK to deployment on Azure
4. change all the file dir locally in prediction.py 

# Cloning the Repository
To clone this repository, follow these steps:

1. Open your terminal or command prompt.
2. Navigate to the directory where you want to save the project.
3. Run the following command:

    ```sh
    git clone https://github.com/username/repository-name.git
    ```

    Replace `https://github.com/username/repository-name.git` with the actual URL of the repository.

4. After cloning, navigate to the newly created project directory:

    ```sh
    cd repository-name
    ```

For the other 2 applications:
1. FE Mobile (Android Java) - suggest to run it normally on Android Studio, no special packages/command line instructions required
2. BE (Java Spring Boot) - suggest to run it normally on IntelliJ, no special packages/command line instructions required

# Credits
Built by Soh Yong Sheng, Thet Naung Soe, Chen Yiqiu (Sophie), Zhao Ziyang, Sun Tianrui (Ray) and Lin Zeyu
Submitted as capstone project for NUS-ISS Graduate Diploma in Systems Analysis (SA58)