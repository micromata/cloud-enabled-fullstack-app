<script lang="ts">
    import "../main.css"
    import {auth} from "$lib/user";
    import NavigationBar from "$lib/components/NavigationBar.svelte";
    import type {PageData} from "./$types";
    import {setup} from "$lib/notifications";
    import {browser} from "$app/environment";
    export let data: PageData;

    $auth = data.user;

    if (browser) {
        setup()
    }
</script>

<NavigationBar/>

<slot/>