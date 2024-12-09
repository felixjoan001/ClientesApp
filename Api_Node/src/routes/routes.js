import express from 'express'
import controller from '../controller/controller.js'

const router = express.Router()

/* Person */
router.get('/api/person', controller.getPersons)

router.get('/api/person/:id', controller.getPersonId)

router.post('/api/create/person', controller.createPerson)

router.put('/api/person/update/:id', controller.updatePerson)

router.delete('/api/person/delete/:id', controller.deletePerson)


/* Store */

router.get('/api/store', controller.getStores)

router.put('/api/store/update/:id', controller.updateStore)

router.get('/api/store/:id', controller.getStoreId)

router.post('/api/create/store', controller.createStore)

router.delete('/api/store/delete/:id', controller.deleteStore)

/* Vendor */

router.get('/api/vendor', controller.getVendors)

router.get('/api/vendor/:id', controller.getVendorId)

router.put('/api/vendor/update/:id', controller.updateVendor)

router.post('/api/create/vendor', controller.createVendor)

router.delete('/api/delete/vendor/:id', controller.deleteVendor)

export default router