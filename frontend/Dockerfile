# FROM node:slim as node
# RUN mkdir /project
# WORKDIR /project

# COPY . .

# RUN npm install -g @angular/cli
# RUN yarn install
# CMD ["ng", "serve", "--host", "frontend", "--port", "4200", "--disable-host-check", "--proxy-config", "proxy.conf.json"]

FROM node:alpine as builder
WORKDIR '/app'
COPY ./package.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx
EXPOSE 4200
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist /usr/share/nginx/html