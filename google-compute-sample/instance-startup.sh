#!/bin/sh

# Set the metadata server to the get projct id
PROJECTID=cdbg-gae-kotlin
BUCKET=cdbg-gae-kotlin


echo "Project ID: ${PROJECTID} Bucket: ${BUCKET}"

# Get the files we need
gsutil cp gs://${BUCKET}/demo.jar .

# Install dependencies
apt-get update
apt-get -y --force-yes install openjdk-8-jdk

# Make Java 8 default
update-alternatives --set java /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java

# Install custom cdbg instance
git clone https://github.com/cbaueratwork/cloud-debug-java
pushd cloud-debug-java
chmod +x build.sh
./build.sh
mkdir -p /opt/cdbg
tar -xvzC /opt/cdbg -f cdbg_java_agent_gce.tar.gz

# Start server
java -agentpath:/opt/cdbg/cdbg_java_agent.so -Dcom.google.cdbg.module="Kotlin Demo" -Dcom.google.cdbg.version="1.0" -jar demo.jar


