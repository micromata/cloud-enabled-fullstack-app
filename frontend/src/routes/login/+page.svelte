<script>
    import Input from "$lib/components/Input.svelte";
    import SSO from "$lib/components/SSO.svelte";
    import Divider from "$lib/components/Divider.svelte";

    export let form;
</script>
<div class="flex items-center pb-80 justify-center h-screen">
    <div class="w-full max-w-md bg-white shadow-md rounded-md p-6">
        <h1 class="text-3xl font-semibold text-center mb-4">Login</h1>
        <SSO/>
        <Divider text="or"/>

        <form action="?/username" method="post">
            <Input label="Username" name="username" type="text"/>

            <Input label="Password" name="password" type="password" errors={form?.error ? [form.error] : undefined} errorType="message"/>


            <button class="w-full bg-indigo-500 text-white py-2 rounded-md hover:bg-indigo-600 focus:outline-none" type="submit">
                Sign In
            </button>
        </form>

        <div class="pt-3">
            Don't have an Account? Create one
            <a href="../register" class="text-blue-700 underline">here</a>
        </div>

    </div>
</div>
