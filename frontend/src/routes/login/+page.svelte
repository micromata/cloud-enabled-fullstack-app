<script>
    import sendToBackend from './+page.server.ts';

    let username = '';
    let password = '';

    const sendDataToBackend = async () => {
        const data = { username, password };

        try {
            const result = await sendToBackend(data);
            console.log(result);
        } catch (error) {
            console.error('Fehler beim Senden der Daten:', error);
        }
    };
</script>

<div class="place-content-center">
    <card class="w-4/5 bg-amber-300">
        <div class="flex-col">
            <label class="text-center block">Benutzername
                <input class="mx-auto block border-2" bind:value={username}>
            </label>
            <label class="text-center block">Passwort
                <input class="mx-auto block border-2" bind:value={password}>
            </label>
            <button class="mx-auto block rounded-full" on:click="{sendDataToBackend}">Einloggen</button>
        </div>
    </card>
</div>