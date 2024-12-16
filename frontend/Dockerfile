# Step 1: Use the official Node.js image as the base image
FROM node:18 AS build

# Step 2: Set the working directory
WORKDIR /app

# Step 3: Copy package.json and package-lock.json files
COPY ./package.json ./package-lock.json ./

# Step 4: Install dependencies
RUN npm install

# Step 5: Copy the rest of the frontend files
COPY . /app

# Step 6: Define the build argument for Google Client ID
ARG VITE_GOOGLE_CLIENT_ID

# Step 7: Set the environment variable for Google Client ID
ENV VITE_GOOGLE_CLIENT_ID=$VITE_GOOGLE_CLIENT_ID

# Step 8: Build the React app with the environment variable
RUN npm run build

# Step 9: Set up Nginx to serve the built app
FROM nginx:alpine

# Step 10: Copy the build output from the previous stage to the Nginx container
COPY --from=build /app/dist /usr/share/nginx/html

# Step 11: Expose port 80
EXPOSE 80

# Step 12: Start the Nginx server to serve the app
CMD ["nginx", "-g", "daemon off;"]
