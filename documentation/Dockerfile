# Use a lightweight Nginx image
FROM nginx:alpine

# Copy your API spec from the /specs directory into the web server's folder
COPY ./specs/openapi.yaml /usr/share/nginx/html/openapi.yaml

# Copy the Scalar HTML file into the web server's folder
COPY ./documentation/index.html /usr/share/nginx/html/index.html