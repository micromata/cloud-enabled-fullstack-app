<script lang="ts">
    import {createEventDispatcher, onMount} from 'svelte';

    const dispatch = createEventDispatcher();

    export let url: string;

    onMount(async () => {
        let script = document.createElement("script");

        script.addEventListener('load', () => {
            dispatch('loaded');
        })

        script.addEventListener('error', (event) => {
            console.error("something went wrong", event);
            dispatch('error');
        });

        script.src = url;

        document.head.appendChild(script);
    });
</script>