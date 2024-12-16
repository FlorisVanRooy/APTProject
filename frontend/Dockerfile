# Stage 1: Build the application
FROM node:18 AS build
WORKDIR /frontend
COPY package*.json ./
RUN npm install
COPY . .

# Accept the Google Client ID during the build
ARG VITE_GOOGLE_CLIENT_ID
ENV VITE_GOOGLE_CLIENT_ID=$VITE_GOOGLE_CLIENT_ID

# Write the VITE_GOOGLE_CLIENT_ID to the .env file in the frontend directory
RUN echo "VITE_GOOGLE_CLIENT_ID=$VITE_GOOGLE_CLIENT_ID" >> .env

# Run the build (ensure dist folder is generated)
RUN npm run build

# Stage 2: Serve the application using nginx
FROM nginx:stable
COPY --from=build /frontend/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
