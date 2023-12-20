export function getGreeting(): string {
    const currentTime = new Date().getHours();

    if (currentTime >= 5 && currentTime < 12) {
        return "Good morning";
    } else if (currentTime >= 12 && currentTime < 17) {
        return "Good afternoon";
    } else if (currentTime >= 17 && currentTime < 20) {
        return "Good evening";
    } else {
        return "Good night";
    }
}