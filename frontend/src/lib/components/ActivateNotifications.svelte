<script lang="ts">
    import {subscribe} from "$lib/notifications";

    export let formResponse: any;
    export let activeSettings: string[];

    let notificationActivation = "";
    let form: HTMLFormElement;

    const activateNotifications = async () => {
        notificationActivation = JSON.stringify(await subscribe());
        setTimeout(() => form.submit());
    };
</script>

{#if (!activeSettings.includes("NOTIFICATIONS_ENABLED"))}
    <p>Notifications are disabled.</p>
    <button class="bg-indigo-500 text-white px-4 py-2 rounded disabled:bg-gray-400 hover:bg-indigo-400 transition-colors focus:ring-1 ring-indigo-600" on:click={activateNotifications}>Enable notifications</button>
    <form bind:this={form} action="?/activeNotifications" method="post">
        <input type="hidden" name="body" value={notificationActivation}>
    </form>
{:else}
    <p>Active Notifications:</p>
    <ul class="list-disc pl-5">
    {#each activeSettings as activeSetting}
        {#if (activeSetting.startsWith("NOTIFICATION_"))}
            <li>{activeSetting.slice("NOTIFICATION_".length)}</li>
        {/if}
    {/each}
    </ul>
{/if}
{#if (formResponse?.error)}
    <span class="text-red-500">{formResponse?.error}</span>
{/if}