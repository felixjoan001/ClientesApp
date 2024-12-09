import express from 'express'
import compression from 'compression'
import bodyParser from 'body-parser'
import router from './routes/routes.js'
const app = express()
// compression() sirve para comprimir los archivos que se envían al cliente
app.use(compression())

// Middleware para manejar JSON
app.use(bodyParser.json()); // Aplica solo si esperas JSON en el cuerpo
app.use(bodyParser.urlencoded({ extended: true })); // Para manejar datos de formularios
// Ruta principal
app.use('/', router)

app.listen(3000, () => {
   console.log(`Server running on port http://192.168.100.2:3000`)
})
