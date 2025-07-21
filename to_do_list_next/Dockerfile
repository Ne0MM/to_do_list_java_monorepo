# Estágio de construção
FROM node:22-alpine AS builder

WORKDIR /app

# Copia package.json e yarn.lock/package-lock.json primeiro para alavancar o cache do Docker
COPY package.json yarn.lock* ./
# ou COPY package.json package-lock.json ./

RUN yarn install --frozen-lockfile
# ou RUN npm ci

COPY . .

# Construa o aplicativo Next.js
# Certifique-se de que NEXT_PUBLIC_ env vars necessários para o build sejam definidos aqui, se houver
ENV NEXT_TELEMETRY_DISABLED 1
RUN npm run build
# ou RUN npm run build

# Estágio de produção
FROM node:22-alpine AS runner

WORKDIR /app

# Define NODE_ENV para produção para otimizações do Next.js
ENV NODE_ENV production
# Desabilita a telemetria do Next.js em produção
ENV NEXT_TELEMETRY_DISABLED 1

# Copia os arquivos de construção do estágio anterior
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/public ./public
COPY --from=builder /app/package.json ./package.json

# Se você tiver arquivos que precisam ser acessados em tempo de execução
# como variáveis de ambiente no .env.production ou .env.local que NÃO são publicas,
# você precisará garantir que elas sejam passadas para o contêiner ou carregadas de outra forma.
# Para variáveis de ambiente que são lidas pelo Next.js em tempo de execução,
# elas geralmente precisam ser passadas para o comando `start` ou definidas no Dockerfile.

# Expõe a porta que o Next.js escuta (padrão é 3000)
EXPOSE 3000

# O comando para iniciar o aplicativo Next.js
CMD ["npm", "start"]