# Ludolang Backend

## Table of Contents
1. [Overview](#overview)
2. [Project Setup](#project-setup)
3. [Tests Setup](#setting-up-tests)
3. [Services Structure](#directory-structure)
4. [Services](#services)

## Overview
This repository contains the backend code for Ludolang, a language learning website inspired by Duolingo.

It is a non-commercial project, intended to showcase my skills and to hopefully help others make similar apps.

The project is written using Kotlin 1.9.25 and Java 21. It uses PostgreSQL as a database.

## Requirements
- Docker
- A Google OAuth Client ID & Credentials

## Project Setup

If you are only interested in running the project locally, I have provided a setup that creates a local PostgreSQL instance with the schema & pre-seeded with a demo user / course. With this, you'll have access to all features except AI and Google authentication.

1. Clone the project
```
git@github.com:jokerhutt/ludolang-backend.git
```
2. Navigate to project directory
```
cd /path/to/ludolang-backend
```
3. Copy & Paste the `example.env` into a `.env` file in the project directory

4. Place your google client ID and google client secret in the `.env` file (See [Setting up Google Oauth](#setting-up-google-oauth))
5. Optionally adjust the `FRONTEND_ORIGINS` and `AUTH_COOKIE_DOMAIN` to match your frontend url.

6. Run the PostgreSQL Container
```
 docker compose -f docker-compose.db.yml up -d
```
7. Run the Application Container (Might take ~60sec)
```
docker compose -f docker-compose.ludocode.yml build ludocode-backend
docker compose -f docker-compose.ludocode.yml up -d ludocode-backend
```

### Setting up Google OAuth
**This only affects authentication. Leaving the `GOOGLE_CLIENT_ID` and `GOOGLE_CLIENT_SECRET` blank means you will need to use the demo user authentication.**
1. Create a Google Cloud account
2. Create a Google Cloud project
3. Go to Google Cloud Console → APIs & Services → Credentials
4. Create OAuth 2.0 Client (type: Web application)
   Required fields:
- Authorized JavaScript origins:
  https://your-frontend-domain.com
- Authorized redirect URIs:
  http://your-frontend-domain.com/auth/google/callback
5. Set `GOOGLE_CLIENT_ID` to your Google client ID and `GOOGLE_CLIENT_SECRET` to your Google client secret.
6. Generate a JWT secret and set `JWT_SECRET` to its value in your environment variables

---
### Setting up Tests
- The test suite uses **Test containers**. Ensure Docker is installed and running before executing tests. (Ensure Docker Desktop on MacOS / Windows)
- All containers (MySQL, etc.) will be started automatically during the test run.

---
## Directory Structure
```
api/
  controllers/   # HTTP controllers
  filters/       # Authenticate requests, check feature flags
  dto/           # request/response DTOs

app/
  mapper/        # entity -> DTO mapping
  service/       # application business logic

configuration/   # configuration beans

domain/
  entity/        # core repository entities
    embeddable/  # embeddable composite keys for entities
  enums/         # domain related enums

infra/
  projection/    # repository projections
  repository/    # jpa repositories for domain entities
  http/          # HTTP clients for calling external APIs (google, etc.)
```