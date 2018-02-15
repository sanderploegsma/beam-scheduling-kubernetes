# Scheduled Apache Beam jobs using Kubernetes Cronjobs

[![](https://img.shields.io/docker/automated/sanderp/beam-scheduling-kubernetes.svg)](https://hub.docker.com/r/sanderp/beam-scheduling-kubernetes/)

## Prerequisites

- Kubernetes cluster version >= 1.8
- Docker (optional, only needed when building your own image)
- Java (optional, only needed to run the application on your own machine)

## Setting up
Deploy the Kubernetes Cronjob using

    kubectl apply -f kubernetes/cronjob.yml