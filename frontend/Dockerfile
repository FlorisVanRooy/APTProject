# Stage 1: Build the application
FROM node:18 AS build
WORKDIR /frontend  # Set working directory to /frontend
COPY package*.json ./
RUN npm install
COPY . .

# Accept the Google Client ID during the build
ARG VITE_GOOGLE_CLIENT_ID
ENV VITE_GOOGLE_CLIENT_ID=$VITE_GOOGLE_CLIENT_ID

# Write the VITE_GOOGLE_CLIENT_ID to the .env file in the frontend directory
RUN echo "VITE_GOOGLE_CLIENT_ID=$VITE_GOOGLE_CLIENT_ID" >> .env

# Stage 2: Serve the application
FROM nginx:stable
COPY --from=build /frontend/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
