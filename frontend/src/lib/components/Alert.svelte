<script lang="ts">
    export let headline: string;
    export let message:string;
    export let linkText:string = "";
    export let link:string = "";
</script>

<div class="bg-blue-100 border border-blue-400 text-blue-700 px-4 py-3 rounded relative" role="alert">
    <strong class="font-bold">{headline}</strong>
    <span class="block sm:inline">{message}</span>
    <a href="{link}" class="block sm:inline underline">{linkText}</a>
    <span class="absolute top-0 bottom-0 right-0 px-4 py-3"></span>
</div>