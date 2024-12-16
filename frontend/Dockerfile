# Stage 1: Build the application
FROM node:18 AS build
WORKDIR /frontend
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Pass the environment variable to the build step
ARG VITE_GOOGLE_CLIENT_ID
RUN echo "VITE_GOOGLE_CLIENT_ID=$VITE_GOOGLE_CLIENT_ID" > .env

# Stage 2: Serve the application with Nginx
FROM nginx:stable

# Copy the built app from the build stage
COPY --from=build /frontend/dist /usr/share/nginx/html

# No need to copy the .env file into the Nginx image (unless you need it)
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
