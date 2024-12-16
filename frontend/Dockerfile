# Step 1: Use the official Node.js image as the base image
FROM node:18 AS build

# Step 2: Set the working directory for the build process
WORKDIR /app

# Step 3: Copy the package.json and package-lock.json files to the working directory
COPY package.json package-lock.json ./

# Step 4: Install the dependencies
RUN npm install

# Step 5: Copy the entire frontend project files into the container
COPY . /app

# Step 6: Build the React app with the environment variable (VITE_GOOGLE_CLIENT_ID)
ARG VITE_GOOGLE_CLIENT_ID
ENV VITE_GOOGLE_CLIENT_ID=$VITE_GOOGLE_CLIENT_ID

RUN npm run build

# Step 7: Set up Nginx to serve the built app
FROM nginx:alpine

# Step 8: Copy the build output from the previous stage to the Nginx container
COPY --from=build /app/build /usr/share/nginx/html

# Step 9: Expose port 80
EXPOSE 80

# Step 10: Start the Nginx server to serve the app
CMD ["nginx", "-g", "daemon off;"]
