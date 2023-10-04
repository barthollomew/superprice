# Use an official Node.js runtime as the parent image
FROM node:16

# Set the working directory in Docker
WORKDIR /app

# Copy package.json and package-lock.json for npm install
COPY package*.json ./

# Install the application's dependencies
RUN npm install

# Copy the rest of the application's code files
COPY . ./

# Build the Next.js app
RUN npm run build

# Specify the command to run when the container starts
CMD ["npm", "start"]
