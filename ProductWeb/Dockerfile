#stage 1
FROM node:latest as node
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
# install and app dependencies
COPY package.json /app/package.json
RUN npm install
RUN npm install -g @angular/cli
RUN npm install get-intrinsic
# add app
COPY . /app
RUN npm run sonar
RUN npm run build --prod
EXPOSE 4200
CMD ng serve --host 0.0.0.0
# ENTRYPOINT ["ng", "serve", "--host", "0.0.0.0"]

#stage 2
# FROM nginx:alpine
# COPY --from=node /app/dist/demo-app /usr/share/nginx/html
