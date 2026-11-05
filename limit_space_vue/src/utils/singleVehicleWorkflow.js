// Send only editable fields and distinct string identifiers; never send browser-supplied roles/status.
export const vehicleFields = ['enterpriseId', 'plateNumber', 'vin', 'plateColor', 'vehicleType',
  'engineNumber', 'manufacturer', 'model', 'businessType', 'registrationDate', 'remarks'];
export function vehicleIdentity(record) {
  const result = {};
  ['id', 'vehicleId', 'letterGid', 'revision'].forEach(key => {
    if (record[key]) result[key] = String(record[key]);
  });
  return result;
}
export function vehicleDraftPayload(record, form) {
  const result = vehicleIdentity(record);
  vehicleFields.forEach(key => { result[key] = form[key] || ''; });
  return result;
}
export function blankVehicleForm() {
  return Object.fromEntries(vehicleFields.map(key => [key, '']));
}
export function openSingleVehicle(vm, row, mode) {
  if (row.typeChild !== '道路运输证核发') return false;
  vm.$router.push({path: '/frontPage/vehicleWorkflow_index', query: {
    ...(row.letterGid ? {letterGid: String(row.letterGid)} : {}),
    ...(row.id ? {id: String(row.id)} : {}), mode
  }});
  return true;
}
