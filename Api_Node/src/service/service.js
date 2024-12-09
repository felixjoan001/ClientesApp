import model from '../model/model.js'

async function getPersons() {
   try {
      const persons = await model.getPersons()
      return persons
   } catch (err) {
      throw new Error('Error al obtener personas')
   }
}

async function getPersonId(id) {
   try {
      const person = await model.getPersonId(id)
      return person
   } catch (err) {
      throw new Error('Error al obtener persona')
   }
}

async function createPerson(person) {
   try {
      const newPerson = await model.createPerson(person)
      return newPerson
   } catch (err) {
      throw new Error('Error al crear nueva persona service'+ err)
   }
}

async function updatePerson(person,id) {
   try {
      const updatePerson = await model.updatePerson(person,id)
      return updatePerson
   } catch (err) {
      throw new Error('Error al actualizar persona service')
   }
}

async function deletePerson(id) {
   try {
      const deletePerson = await model.deletePerson(id)
      return deletePerson
   } catch (err) {
      throw new Error('Error al eliminar persona service')
   }
}

async function getStores() {
   try {
      const stores = await model.getStores()
      return stores
   } catch (err) {
      throw new Error('Error al obtener tiendas')
   }
}

async function updateStore(store, id) {
   try {
      const updateStore = await model.updateStore(store, id)
      return updateStore
   } catch (err) {
      throw new Error('Error al actualizar tienda')
   }
}

async function getStoreId(id) {
   try {
      const store = await model.getStoreId(id)
      return store
   } catch (err) {
      throw new Error('Error al obtener tienda')
   }
}

async function createStore(store) {
   try {
      const newStore = await model.createStore(store)
      return newStore
   } catch (err) {
      throw new Error('Error al crear nueva tienda')
   }
}

async function deleteStore(id) {
   try {
      const deleteStore = await model.deleteStore(id)
      return deleteStore
   } catch (err) {
      throw new Error('Error al eliminar tienda')
   }
}

//Endpoint de Vendors


async function getVendors() {
   try {
      const vendors = await model.getVendors()
      return vendors
   } catch (err) {
      throw new Error('Error al obtener vendedores')
   }
}

async function getVendorId(id) {
   try {
      const vendor = await model.getVendorId(id)
      return vendor
   } catch (err) {
      throw new Error('Error al obtener ')
   }
}

async function updateVendor(vendor, id) {
   try {
      const updateVendor = await model.updateVendor(vendor, id)
   
      return updateVendor
   } catch (err) {
      throw new Error('Error al actualizar vendedor')
    }
}

async function createVendor(vendor) {
   try {
      const newVendor = await model.createVendor(vendor)
      return newVendor
   } catch (err) {
      throw new Error('Error al crear nuevo vendedor')
   }
}

async function deleteVendor(id){
   try {
      const deleteVendor = await model.deleteVendor(id)
      return deleteVendor
   } catch (err) {
      throw new Error('Error al eliminar vendedor')
   }
}

export default {
   getPersons,
   getPersonId,
   createPerson,
   updatePerson,
   deletePerson,
   getStores,
   updateStore,
   getStoreId,
   createStore,
   deleteStore,
   getVendors,
   getVendorId,
   updateVendor,
   createVendor,
   deleteVendor
}