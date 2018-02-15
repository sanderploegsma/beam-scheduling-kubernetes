# Scheduled Apache Beam jobs using Kubernetes Cronjobs

[![](https://img.shields.io/docker/automated/sanderp/beam-scheduling-kubernetes.svg)](https://hub.docker.com/r/sanderp/beam-scheduling-kubernetes/)

## Prerequisites

- Docker
- A Kubernetes cluster version >= 1.8
- Java (optional, only needed to run locally)

## Setting up
Deploy the Kubernetes Cronjob using

    kubectl apply -f kubernetes/cronjob.yml