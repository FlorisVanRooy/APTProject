#!/bin/sh

# Check if the environment variable is available
if [ -z "$VITE_GOOGLE_CLIENT_ID" ]; then
  echo "VITE_GOOGLE_CLIENT_ID is not set"
  exit 1
fi

# Inject the GOOGLE_CLIENT_ID environment variable into the Vite-generated index.html file
sed -i "s|__VITE_GOOGLE_CLIENT_ID__|$VITE_GOOGLE_CLIENT_ID|g" /usr/share/nginx/html/index.html
