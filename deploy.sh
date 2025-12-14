#!/bin/bash
set -e

VM_USER=jhutcode
VM=instance-20251214-160731
ZONE=europe-west1-d
REMOTE_DIR=/home/jhutcode/duoclone

echo "🔨 Building JAR..."
./mvnw clean package -DskipTests

echo "📂 Ensuring remote dir exists..."
gcloud compute ssh ${VM_USER}@${VM} --zone=${ZONE} --command \
"mkdir -p ${REMOTE_DIR}"

echo "📦 Copying files..."
gcloud compute scp \
  ./target/duoclone-backend-*.jar \
  ./Dockerfile \
  ./docker-compose.duoclone.yml \
  ./.env \
  ${VM_USER}@${VM}:${REMOTE_DIR} \
  --zone=${ZONE}

echo "🚀 Restarting duoclone only..."
gcloud compute ssh ${VM_USER}@${VM} --zone=${ZONE} --command \
"cd ${REMOTE_DIR} && \
 sudo docker-compose -f docker-compose.duoclone.yml down && \
 sudo docker-compose -f docker-compose.duoclone.yml up -d --build"

read -p '📝 Tail logs? (y/n): ' yn
if [[ $yn == y ]]; then
  gcloud compute ssh ${VM_USER}@${VM} --zone=${ZONE} --command \
  "sudo docker logs -f duoclone-backend"
fi