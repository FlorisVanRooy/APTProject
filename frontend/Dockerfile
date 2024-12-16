# Stage 1: Build the application
FROM node:18 AS build

# Set working directory
WORKDIR /app

# Accept build arguments
ARG VITE_GOOGLE_CLIENT_ID

# Set environment variables (if needed)
ENV VITE_GOOGLE_CLIENT_ID=$VITE_GOOGLE_CLIENT_ID

# Copy package.json and install dependencies
COPY package.json ./
RUN npm install

# Copy the rest of the app's files
COPY . .

# Build the app
RUN npm run build

# Stage 2: Serve the application using the 'serve' package
FROM node:18

# Install the 'serve' package globally
RUN npm install -g serve

# Set working directory
WORKDIR /app

# Copy the build from the previous stage
COPY --from=build /app/build /app/build

# Expose the build directory
EXPOSE 3000

# Start the app
CMD ["npm", "start"]
