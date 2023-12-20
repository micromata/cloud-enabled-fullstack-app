<script lang="ts">
    export let headline: string;
    export let message:string;
    export let linkText:string | null = null;
    export let link:string | null = null;
    export let color:string = "blue";

</script>

<div class={`bg-${color}-100 border border-${color}-400 text-${color}-700 px-4 py-3 rounded relative`} role="alert">
    <strong class="font-bold">{headline}</strong>
    <span class="block sm:inline">{message}</span>
    {#if link}
    <a href="{link}" class="block sm:inline underline">{linkText}</a>
        {/if}
    <span class="absolute top-0 bottom-0 right-0 px-4 py-3"></span>
</div>