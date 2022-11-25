#stage 1
FROM node:latest as node
WORKDIR /app
RUN npm install -g @angular/cli
RUN npm install get-intrinsic
RUN npm run build --prod
COPY . .
EXPOSE 4200
ENTRYPOINT ["ng", "serve", "--host", "0.0.0.0"]

#stage 2
# FROM nginx:alpine
# COPY --from=node /app/dist/demo-app /usr/share/nginx/html
