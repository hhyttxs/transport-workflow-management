// Calendar dates must not shift when restored in a timezone west of UTC.
export function licenseDateForPicker(value) {
  if (!value) return '';
  const iso=String(value).replace(/^(\d{4})(\d{2})(\d{2})$/, '$1-$2-$3');
  if (!/^\d{4}-\d{2}-\d{2}$/.test(iso)) throw new Error('无效的营业执照日期');
  const [y,m,d]=iso.split('-').map(Number), date=new Date(y,m-1,d);
  if(date.getFullYear()!==y || date.getMonth()!==m-1 || date.getDate()!==d) throw new Error('无效的营业执照日期');
  return date;
}
export function licenseDateForStorage(value) {
  if (!value) return '';
  const d=value instanceof Date ? value : licenseDateForPicker(value);
  if(Number.isNaN(d.getTime())) throw new Error('无效的营业执照日期');
  return `${d.getFullYear()}-${String(d.getMonth()+1).padStart(2,'0')}-${String(d.getDate()).padStart(2,'0')}`;
}
