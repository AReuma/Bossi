export function numberWithCommas(value) {
    if (value && typeof value.toString === 'function') {
        return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
    return value;
}