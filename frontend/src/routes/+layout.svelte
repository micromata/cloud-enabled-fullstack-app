<script>
    import "../main.css"
    import {auth} from "$lib/user";
    import NavigationBar from "$lib/components/NavigationBar.svelte";

    export let data;

    $auth = data.user;
</script>

<NavigationBar/>

<slot/>