const EXPECTED_COLUMNS = [
  'GroupNo',
  'Consultant/ClientName',
  'Student#',
  'Surname',
  'FullName',
  'Attendance rate%'
];

export function validateCSV(fileText) {
  const errors = [];
  const lines = fileText.trim().split('\n');

  if (lines.length < 2) {
    errors.push('File is empty or has no data rows.');
    return errors;
  }

  const headers = lines[0].split(',').map(h => h.trim());

  EXPECTED_COLUMNS.forEach((expected, i) => {
    if (headers[i] !== expected) {
      errors.push(`Column ${i + 1}: expected "${expected}", found "${headers[i] ?? 'nothing'}".`);
    }
  });

  if (errors.length > 0) return errors;

  lines.slice(1).forEach((line, rowIndex) => {
    const row = line.split(',').map(v => v.trim());
    const rowNum = rowIndex + 2;

    if (!/^\d+$/.test(row[0]))          errors.push(`Row ${rowNum}: GroupNo must be a whole number.`);
    if (!/^\d+$/.test(row[2]))          errors.push(`Row ${rowNum}: Student# must be a whole number.`);
    if (isNaN(parseFloat(row[5])))      errors.push(`Row ${rowNum}: Attendance rate% must be a number.`);
  });

  return errors;
}