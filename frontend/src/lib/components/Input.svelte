<script lang="ts">
    export let label: string;
    export let name: string;
    export let type: string;

    export let errors: string[] | undefined = undefined;
    export let errorType: 'requirements' | 'message' = "requirements";

    export let value: any = null;

    export let basicValue: string = "";
</script>

<div class="mb-4">
    <label class="block text-gray-700 text-sm font-semibold mb-2">
        {label}
        {#if type !== "longtext"}
            <input name={name} value="{basicValue}" class:border-red-600={errors} on:change={e => value = e.target?.value} class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type={type}>
        {:else}
            <textarea name={name} value="{basicValue}" class:border-red-600={errors} on:change={e => value = e.target?.value} rows="10" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500"></textarea>
        {/if}
    </label>
    {#if (errors)}
        {#if (errorType === "requirements")}
            <div class="text-red-600">
                <p>The following requirements are not met:</p>
                <ul class="list-disc px-10">
                    {#each errors as error}
                        <li>{error}</li>
                    {/each}
                </ul>
            </div>
        {:else if (errorType === "message")}
            {#each errors as error}
                <p class="text-red-600">{error}</p>
            {/each}
        {/if}
    {/if}
</div>