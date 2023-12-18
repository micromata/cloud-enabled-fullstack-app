<script>
    import LibLoader from "$lib/components/LibLoader.svelte";
    import {env} from "$env/dynamic/public";

    let signInWithGoogle;

    let googleSSOKey = "";
    let ssoForm;

    const start = () => {
        console.log("test")
        google.accounts.id.initialize({
            client_id: env.PUBLIC_GOOGLE_CLIENT_ID,
            callback: callback
        });
        //@ts-ignore
        google.accounts.id.renderButton(signInWithGoogle,
            { theme: "outline", size: "large", width: 400 }  // customization attributes
        );
    };

    function callback(c) {
        googleSSOKey = c.credential;
        setTimeout(() => ssoForm.submit());
    }

</script>

<LibLoader url="https://accounts.google.com/gsi/client" on:loaded={start}/>

<div class="mb-4" bind:this={signInWithGoogle}></div>

<form method="post" action="?/sso" bind:this={ssoForm}>
    <input type="hidden" name="sso" value={googleSSOKey}/>
</form>