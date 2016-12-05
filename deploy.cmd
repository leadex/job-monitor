@echo off
kubectl delete deployment jobmonitor-startup-backend
kubectl delete deployment jobmonitor-startup-freelancer
kubectl delete deployment jobmonitor-startup-guru
kubectl delete deployment jobmonitor-startup-notification-manager
kubectl delete deployment jobmonitor-startup-slack
kubectl delete deployment jobmonitor-startup-task-manager
kubectl delete deployment jobmonitor-startup-upwork

kubectl apply -f .\backend\src\main\k8s\backend.yaml
kubectl apply -f .\freelancer\src\main\k8s\freelancer.yaml
kubectl apply -f .\guru\src\main\k8s\guru.yaml
kubectl apply -f .\notificationmanager\src\main\k8s\notification-manager.yaml
kubectl apply -f .\slack\src\main\k8s\slack.yaml
kubectl apply -f .\taskmanager\src\main\k8s\task-manager.yaml
kubectl apply -f .\upwork\src\main\k8s\upwork.yaml
