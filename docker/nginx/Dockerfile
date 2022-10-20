FROM nginx:1.17-alpine

ARG DEFAULT_CONF="./docker/nginx/default.conf"

COPY ${DEFAULT_CONF} /etc/nginx/conf.d/default.conf
COPY ./docker/nginx/fallback.html /usr/share/nginx/html/

CMD ["nginx", "-g", "daemon off;"]
