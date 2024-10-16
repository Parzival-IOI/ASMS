# GETTING STARTED WITH ADVANCE SCHOOL MANAGEMENT SYSTEM

## Setup Key for JWT Login
+ Open bash shell (recommended bash shell for openssl)
+ Run command for generating keypair.pem

      openssl genrsa -out keypair.pem 2048
+ Run command for generation public.pem (public key)

      openssl rsa -in keypair.pem -pubout -out public.pem
+ Run command for generating private.pem (private key)

      openssl pkcs8 -topk8 -inform PEM -nocrypt -in keypair.pem -out private.pem
+ After Complete you can copy private and public key to .env file

> #### Note:
> + Copy String from file name public and private to their respective name in .env

## Setup Environment File

### .env

    DATABASE_URL=
    DATABASE_USERNAME=
    DATABASE_PASSWORD=
    
    ACCESS_MINUTES=
    REFRESH_HOURS=
    
    PUBLIC_KEY=
    PRIVATE_KEY=

### .env.local (Supporting Docker)

    DATABASE_URL=
    DATABASE_USERNAME=
    DATABASE_PASSWORD=
    
    ACCESS_MINUTES=
    REFRESH_HOURS=
    
    PUBLIC_KEY=
    PRIVATE_KEY=


> #### Note:
> + When using .env.local make sure your database url using service name instead of localhost
> + Ex:
>   + Normal .env
>     + DATABASE_URL=jdbc:postgresql://localhost:5432/ASMS
>   + .env.local
>     + DATABASE_URL=jdbc:postgresql://postgres:5432/ASMS

## Docker

To Run docker you need to config .env.local file first

After configure go to terminal and Run command step by step below

+ Build Docker Container

      docker compose build
+ Run/Start Container

      docker compose up

