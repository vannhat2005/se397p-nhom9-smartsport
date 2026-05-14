export function formatTime(time) {
    if (!time) return "";

    // "06:00:00" -> "06:00"
    return time.substring(0, 5);
}