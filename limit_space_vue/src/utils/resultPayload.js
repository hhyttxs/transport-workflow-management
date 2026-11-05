export function resultPayload(response) { return response && response.code !== undefined ? response : response.data; }
