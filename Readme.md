## Authorization service for SA final project


Environment variables used in this application,

- JWT_PREFIX
- JWT_SECRET
- JWT_ISSUER
- DB_USERNAME
- DB_PASSWORD
- DB_HOST
- DATABASE_NAME

Note that when running this application as container these environment variables could be passed for application to function properly.

```sh
    docker run -it \
        -e JWT_PREFIX=<prefix> -e JWT_SECRET=<secret>  -e JWT_ISSUER=<issuer> \
        -e DB_USERNAME=root -e DB_PASSWORD=test -e DB_HOST=localhost:3306 -e DATABASE_NAME=UserService
        ghcr.io/rust42/sa-finalproject-userservice:main userservice
```


If the environment variables are not passed the application uses default values.