import tkinter as tk
import cv2
import os
import csv
import numpy as np
from PIL import Image
import pandas as pd
import datetime
import time

# Initialize window
window = tk.Tk()
window.title("Face Recognition Attendance System")
window.geometry('800x500')
window.configure(background='black')

# Labels and input fields
tk.Label(window, text="EMP ID", fg="white", bg="green", font=('times', 15, 'bold')).place(x=100, y=50)
txt_id = tk.Entry(window, font=('times', 15))
txt_id.place(x=300, y=50)

tk.Label(window, text="Employee Name", fg="white", bg="green", font=('times', 15, 'bold')).place(x=100, y=100)
txt_name = tk.Entry(window, font=('times', 15))
txt_name.place(x=300, y=100)

message = tk.Label(window, text="", fg="white", bg="green", font=('times', 15, 'bold'))
message.place(x=100, y=400)

# Functions
def clear():
    txt_id.delete(0, 'end')
    txt_name.delete(0, 'end')
    message.config(text="")

def is_number(s):
    try:
        float(s)
        return True
    except ValueError:
        return False

def take_images():
    Id = txt_id.get()
    name = txt_name.get()
    
    if is_number(Id) and name.isalpha():
        cam = cv2.VideoCapture(0)
        face_cascade = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")
        sample_num = 0
        while True:
            ret, img = cam.read()
            gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
            faces = face_cascade.detectMultiScale(gray, 1.3, 5)

            for (x, y, w, h) in faces:
                sample_num += 1
                face_img = gray[y:y+h, x:x+w]
                cv2.imwrite(f"TrainingImage/{name}.{Id}.{sample_num}.jpg", face_img)
                cv2.rectangle(img, (x, y), (x+w, y+h), (255, 0, 0), 2)
                cv2.imshow('Face Capture', img)
            
            if cv2.waitKey(100) & 0xFF == ord('q') or sample_num > 100:
                break

        cam.release()
        cv2.destroyAllWindows()

        # Save employee details
        with open('EmployeeDetails.csv', 'a+', newline='') as csvFile:
            writer = csv.writer(csvFile)
            writer.writerow([Id, name])
        
        message.config(text=f"Images Saved for ID: {Id}, Name: {name}")
    else:
        message.config(text="Invalid Input: ID must be numeric & Name must be alphabetic")

def train_images():
    recognizer = cv2.face.LBPHFaceRecognizer_create()
    faces, ids = get_images_and_labels("TrainingImage")
    recognizer.train(faces, np.array(ids))
    recognizer.save("TrainedModel.yml")
    message.config(text="Training Completed")

def get_images_and_labels(path):
    image_paths = [os.path.join(path, f) for f in os.listdir(path)]
    faces, ids = [], []

    for imagePath in image_paths:
        img = Image.open(imagePath).convert('L')
        image_np = np.array(img, 'uint8')
        Id = int(os.path.split(imagePath)[-1].split(".")[1])
        faces.append(image_np)
        ids.append(Id)
    
    return faces, ids

def track_images():
    recognizer = cv2.face.LBPHFaceRecognizer_create()
    recognizer.read("TrainedModel.yml")
    face_cascade = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")
    df = pd.read_csv("EmployeeDetails.csv")
    
    cam = cv2.VideoCapture(0)
    attendance = pd.DataFrame(columns=['Id', 'Name', 'Date', 'Time'])

    while True:
        ret, img = cam.read()
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        faces = face_cascade.detectMultiScale(gray, 1.2, 5)

        for (x, y, w, h) in faces:
            Id, conf = recognizer.predict(gray[y:y+h, x:x+w])
            if conf < 50:
                ts = time.time()
                date = datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d')
                timeStamp = datetime.datetime.fromtimestamp(ts).strftime('%H:%M:%S')
                name = df.loc[df['Id'] == Id]['Name'].values[0]
                attendance.loc[len(attendance)] = [Id, name, date, timeStamp]
                text = f"{Id} - {name}"
            else:
                text = "Unknown"
            
            cv2.putText(img, text, (x, y+h), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
            cv2.rectangle(img, (x, y), (x+w, y+h), (255, 0, 0), 2)
        
        attendance.drop_duplicates(subset=['Id'], keep='first', inplace=True)
        cv2.imshow('Face Recognition', img)

        if cv2.waitKey(1) == ord('q'):
            break

    cam.release()
    cv2.destroyAllWindows()

    # Save attendance
    timestamp = datetime.datetime.now().strftime('%Y-%m-%d_%H-%M-%S')
    attendance.to_csv(f"Attendance_{timestamp}.csv", index=False)
    message.config(text="Attendance Recorded")

# Buttons
tk.Button(window, text="Clear", command=clear, fg="red", bg="yellow", font=('times', 12, 'bold')).place(x=600, y=50)
tk.Button(window, text="Take Images", command=take_images, fg="red", bg="yellow", font=('times', 12, 'bold')).place(x=100, y=200)
tk.Button(window, text="Train Images", command=train_images, fg="red", bg="yellow", font=('times', 12, 'bold')).place(x=300, y=200)
tk.Button(window, text="Track Images", command=track_images, fg="red", bg="yellow", font=('times', 12, 'bold')).place(x=500, y=200)
tk.Button(window, text="Quit", command=window.quit, fg="red", bg="yellow", font=('times', 12, 'bold')).place(x=300, y=300)

window.mainloop()
