<script lang="ts">
    import Input from "$lib/components/Input.svelte";

    let password = "";
    let passwordConfirm = "";

    $: passwordValid = password === passwordConfirm

    export let form;

</script>


<div class="flex items-center mt-10 justify-center h-screen">
    <form method="post" class="w-full max-w-md bg-white shadow-md rounded-md p-6">

        <Input name="username" label="Username" type="text" errors={form?.errors?.username}/>

        <Input name="email" label="E-Mail" type="email"/>

        <Input name="password" label="Password" type="password" bind:value={password} errors={form?.errors?.password}/>

        <Input name="passwordConfirm" label="Repeat Password" type="password" bind:value={passwordConfirm} errors={!passwordValid ? ["Password are not identical"] : undefined} errorType="message"/>

        <button class="w-full bg-[#5e60e4] text-white py-2 rounded-md hover:bg-indigo-600 focus:outline-none disabled:bg-gray-400"
                type="submit"
                disabled={!passwordValid}>
            Sign Up
        </button>

        <div class="pt-3">
            Already have an Account? Click
            <a href="../login" class="text-blue-700 underline">here</a>
        </div>


    </form>
</div>