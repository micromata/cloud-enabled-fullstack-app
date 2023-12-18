<script lang="ts">
    import "../main.css"
    import {auth} from "$lib/user";
    import NavigationBar from "$lib/components/NavigationBar.svelte";
    import type {PageData} from "./$types";

    export let data: PageData;

    $auth = data.user;
</script>

<NavigationBar/>

<slot/>