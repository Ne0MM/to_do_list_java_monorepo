#Building a Next.js application with Docker
FROM node:22-alpine AS builder

WORKDIR /app

#Copy package.json and yarn.lock (or package-lock.json) first to leverage Docker cache
COPY package.json package-lock.json* ./

RUN npm install --frozen-lockfile

COPY . .

ENV NEXT_TELEMETRY_DISABLED 1
RUN npm run build

FROM node:22-alpine AS runner

WORKDIR /app

ENV NODE_ENV production

ENV NEXT_TELEMETRY_DISABLED 1

COPY --from=builder /app/.next ./.next
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/public ./public
COPY --from=builder /app/package.json ./package.json

EXPOSE 3000

CMD ["npm", "start"]