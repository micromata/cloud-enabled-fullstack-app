const sendToBackend = async (data: { username: string; password: string }): Promise<string> => {
    const response = await fetch('http://localhost:8080/api/submit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    const result = await response.json();
    return result;
};

export default sendToBackend;