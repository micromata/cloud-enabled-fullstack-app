export function getGreeting(): string {
    const currentTime = new Date().getHours();

    if (currentTime >= 5 && currentTime < 12) {
        return "Good morning";
    } 
    if (currentTime < 17) {
        return "Good afternoon";
    }
     if (currentTime < 20) {
        return "Good evening";
    }
     return "Good night";
}