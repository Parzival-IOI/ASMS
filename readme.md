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
> + Copy from file name public and private

## Setup Environment File

#### Further Maintenance