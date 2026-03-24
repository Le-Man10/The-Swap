# How to run the backend

1. hardware required to run the system.

- Atleast 4.3 GB of free RAM.
- Atleast core i5 10th gen(this is just a rough estimation)
- Fast WIFI(for downloading the AI model).

2. Software required
- Docker
- vs code or intellij
- ollama
- deepseek-r1:1.5b model

3. Running the system
- open your terminal and make sure you are in the same directory as the docker compose file before running this command.  
```
docker compose --env-file .prod.env up -d
```
- this will start the system but you will encounter an error with the api since we havent downloaded an AI model yet
```
docker exec -it ollama ollama pull deepseek-r1:1.5b
```
- the download might take a while so get some coffee and play some jazz as you wait.
- when the download finishes just rerun the docker container for the api(the name of the container should be "spring-theswap-app") and everything should be working fine